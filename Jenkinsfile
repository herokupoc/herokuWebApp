node {  
	
	stage ('build-app')
	{     
		echo 'Running build-app4'
		
		echo 'scm_path parameter =' + branch
		
		
		currentBuild.displayName = currentBuild.number + ' - ' + branch
		
		
		git branch: 'dev', credentialsId: 'ignramgar', url: 'C:\\dev\\workspace\\herokuPOC\\AmHerokuWebApp'
		
		bat 'git log -1' > commit.txt
		
		
		def commitMessage = readFile 'commit.txt'
		echo "Commit message: " + commitMessage
		currentBuild.description  = commitMessage
		
				   
		setMavenThreeAndJavaSeven()  
		
		
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
