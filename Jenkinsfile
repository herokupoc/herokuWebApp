node {  
	
	stage ('build-app')
	{     
		echo 'Running build-app4'
		
		echo 'scm_path parameter =' + branch
		
		
		currentBuild.displayName = currentBuild.number + ' - ' + branch
		
		
		git branch: 'dev', credentialsId: 'ignramgar', url: 'C:\\dev\\workspace\\herokuPOC\\AmHerokuWebApp'
		
		bat 'git log -1 > lastCommit.txt'
		
		
		
		def commitMessage = readFile file: 'lastCommit.txt'
		echo "Commit message: " + commitMessage
		currentBuild.description  = commitMessage
		
				   
		setMavenThreeAndJavaEight()  
		
		bat 'mvn clean package'
		
		
	}


	stage ('deploy-heroku-dev')
	{
		
		def userInput = input(message: 'Deploy to DEV Environment?', ok: 'Continue', 
                        parameters: [booleanParam(defaultValue: false, 
                        description: 'Check Deploy option if you want to deploy this build or Continue if you want to run next stage',name: 'Deploy')])
		echo ("userInput: " + userInput)
		
		if (userInput) 
		{	
			echo 'Running deploy-dev'
			
		}
		
	}

}

/**
 * Set maven 3 and Java 7 in path
 */
def setMavenThreeAndJavaSeven() {
    env.M2_HOME="${tool 'maven-3'}"
    env.JAVA_HOME="${tool 'jdk1.7'}"
    env.PATH="${env.JAVA_HOME}/bin;${env.PATH}"
    env.PATH="${env.M2_HOME}/bin;${env.PATH}"
    echo 'env.PATH: ' + env.PATH
}

/**
 * Set maven 3 and Java 8 in path
 */
def setMavenThreeAndJavaEight() {
    env.M2_HOME="${tool 'maven-3'}"
    env.JAVA_HOME="${tool 'jdk1.8'}"
    env.PATH="${env.JAVA_HOME}/bin;${env.PATH}"
    env.PATH="${env.M2_HOME}/bin;${env.PATH}"
    echo 'env.PATH: ' + env.PATH
}