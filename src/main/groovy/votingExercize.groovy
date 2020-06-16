import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import groovy.json.JsonBuilder


class Votation {

    public static def votesList = [:]
    public static String candidateName
    public static int voteCount = 1


    public static def makeYourVote(){
        while(true) {
            println("Please Enter your candidate's name: ")
            candidateName = System.in.newReader().readLine()
            println("Your vote for ${candidateName} accepted!")

            boolean candidateAlreadyExists = votesList.containsKey(candidateName)
            println(candidateAlreadyExists)

            if (candidateAlreadyExists) {
                voteCount = voteCount + 1
                votesList.put(candidateName, voteCount)
                println("Voting list updated ${votesList}")
            }

            else {
                votesList.put(candidateName, voteCount)
                println("Voting list updated ${votesList}")
            }

            def resultsFile = new JsonBuilder(votesList).toPrettyString()
            new File("./votingResults.json").write(resultsFile)
        }
    }


    public static void main(String[] args) {
        Votation.makeYourVote()
    }
}

