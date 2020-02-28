node {  
	
	stage ('heroku-build-app')
	{     
		echo 'Running build-app4'
		
		echo 'scm_path parameter =' + branch
		
		
		currentBuild.displayName = currentBuild.number + ' - ' + branch
		
		
		git branch: branch, credentialsId: 'ignramgar', url: 'C:\\dev\\workspace\\herokuPOC\\AmHerokuWebApp'
		
		bat 'git log -1 > lastCommit.txt'
		
		
		
		def commitMessage = readFile file: 'lastCommit.txt'
		echo "Commit message: " + commitMessage
		currentBuild.description  = commitMessage
		
				   
		setMavenThreeAndJavaEight()  
		
		bat 'mvn clean package'
		
		
	}


	stage ('heroku-deploy-dev')
	{
		
		def userInput = input(message: 'Deploy to DEV Environment?', ok: 'Continue', 
                        parameters: [booleanParam(defaultValue: false, 
                        description: 'Check Deploy option if you want to deploy this build or Continue if you want to run next stage',name: 'Deploy')])
		echo ("userInput: " + userInput)
		
		if (userInput) 
		{	
			echo 'Running deploy-dev'
			bat 'git push heroku-dev ' + branch + ':master'
		}
		
	}
	
	stage ('heroku-test-dev')
	{
		
		def userInput = input(message: 'Do you want to test DEV app in Heroku?', ok: 'Continue', 
                        parameters: [booleanParam(defaultValue: false, 
                        description: 'Check Tests option if you want to test this build or Continue if you want to run next stage',name: 'Execute tests')])
		echo ("userInput: " + userInput)
		
		if (userInput) 
		{	
			echo 'heroku-test-dev'
		}
		
	}
	
	stage ('heroku-deploy-uat')
	{
		
		def userInput = input(message: 'Deploy to UAT Environment?', ok: 'Continue', 
                        parameters: [booleanParam(defaultValue: false, 
                        description: 'Check Deploy option if you want to deploy this build or Continue if you want to run next stage',name: 'Deploy')])
		echo ("userInput: " + userInput)
		
		if (userInput) 
		{	
			echo 'heroku-deploy-uat'
			bat 'git push heroku-uat ' + branch + ':master'
			if (branch.equals("dev"))
			{
    			echo 'merging to uat'
    			bat 'git checkout uat'
    			bat 'git merge dev'
			}
				
		}
		
	}
	
	stage ('heroku-test-uat')
	{
		
		def userInput = input(message: 'Do you want to test UAT app in Heroku?', ok: 'Continue', 
                        parameters: [booleanParam(defaultValue: false, 
                        description: 'Check Tests option if you want to test this build or Continue if you want to run next stage',name: 'Execute tests')])
		echo ("userInput: " + userInput)
		
		if (userInput) 
		{	
			echo 'heroku-test-uat'
		}
		
	}
	
	stage ('heroku-deploy-prod')
	{
		
		def userInput = input(message: 'Deploy to PROD Environment?', ok: 'Continue', 
                        parameters: [booleanParam(defaultValue: false, 
                        description: 'Check Deploy option if you want to deploy this build or Continue if you want to run next stage',name: 'Deploy')])
		echo ("userInput: " + userInput)
		
		if (userInput) 
		{	
			echo 'heroku-deploy-prod'
			bat 'git push heroku-prod ' + branch + ':master'
		}
		
	}
	
	stage ('heroku-smokeTest-Prod')
	{
		
		def userInput = input(message: 'Smoke Test Executed Properly in PROD?', ok: 'Continue', 
                        parameters: [booleanParam(defaultValue: false, 
                        description: 'Check Deploy option if you want to deploy this build or Continue if you want to run next stage',name: 'Deploy')])
		echo ("userInput: " + userInput)
		
		if (userInput) 
		{	
			echo 'heroku-smokeTest-Prod'
			
		}
		
	}
	stage ('heroku-mergeCode-to-master')
	{	
		echo 'Merging code to Master'	
		bat 'git checkout prod'
    	bat 'git merge uat'
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