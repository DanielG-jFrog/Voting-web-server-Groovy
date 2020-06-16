import com.sun.net.httpserver.HttpServer
import groovy.json.*

int PORT = 3000

String filepath = "./votes.json"

String candidate = null

Map request = [:]

def jsonSlurper = new JsonSlurper()

Map candidatesMap = [:]


def file = new File("./votes.json")

if (file.size() > 0) {
    candidatesMap = jsonSlurper.parse(new File("./votes.json"))
}
else{
    candidatesMap = [:]
}


HttpServer.create(new InetSocketAddress(PORT), /*max backlog*/ 0).with {
    println "Server is listening on ${PORT}, hit Ctrl+C to exit."
    createContext("/vote") { http ->
        http.responseHeaders.add("Content-type", "application/json")
        http.sendResponseHeaders(200, 0)
        http.responseBody.withWriter { out ->
            request = jsonSlurper.parseText(http.requestBody.getText())
            candidate = request.candidate
            if (candidate?.trim()) {
                out << "Your vote is accepted"
        boolean isExistsInResultsFile = candidatesMap.containsKey(candidate)
        if(isExistsInResultsFile)     {
            int voteCount = candidatesMap.getAt(candidate)
            candidatesMap.put(candidate, voteCount + 1)
        }
        else{
            candidatesMap.put(candidate, 1)
        }
        println(candidatesMap)

        def resultsFile = new JsonBuilder(candidatesMap).toPrettyString()
        try {
            new File(filepath).write(resultsFile)
        } catch (FileNotFoundException fne) {
            println("Failed to open file votingResults.json. Reason: ${fne}")
        } catch (Exception e) {
            println("Failed to write to results file! ${e}")
        }
            }
            else{
                out << "Please send a valid candidate name"
            }
        }
        println ("${http.requestMethod.toString()} Request received")
    }

    createContext("/candidates") { http ->
        http.responseHeaders.add("Content-type", "application/json")
        http.sendResponseHeaders(200, 0)
        http.responseBody.withWriter { out ->
            def resutlsFile = jsonSlurper.parse(new File(filepath))
            out << resutlsFile
        }
        println ("${http.requestMethod.toString()} Request received")
    }
    start()
}

