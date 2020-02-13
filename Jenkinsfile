node {  
	def out = 'no'

	stage ('build-app')
	{     
		echo 'Running build-app'
		
		echo 'scm_path parameter =' + scm_path
		
		tempScmVariable= scm_path
		
		if (scm_path == 'javaeightdev') {
			scm_path = 'development'
		}
		echo 'scm_path evaluated =' + scm_path
		
		currentBuild.displayName = currentBuild.number + ' - ' + scm_path
		bat 'svn log -l 1 http://ncecvsmad02/scm/svn/amadeus/gda-online-2/' + scm_path + ' > commit.txt'
		commitMessage = readFile 'commit.txt'
		echo 'Commit message: ' + commitMessage
		currentBuild.description  = commitMessage
		
		if(revision.length()>0) {
			checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '51106c7d-3cda-4be9-a9e9-84ebf8b1bc58', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'http://ncecvsmad02/scm/svn/amadeus/gda-online-2/'+scm_path+'${revision}']], workspaceUpdater: [$class: 'CheckoutUpdater']])
		} else {
			checkout([$class: 'SubversionSCM', additionalCredentials: [], excludedCommitMessages: '', excludedRegions: '', excludedRevprop: '', excludedUsers: '', filterChangelog: false, ignoreDirPropChanges: false, includedRegions: '', locations: [[credentialsId: '51106c7d-3cda-4be9-a9e9-84ebf8b1bc58', depthOption: 'infinity', ignoreExternalsOption: true, local: '.', remote: 'http://ncecvsmad02/scm/svn/amadeus/gda-online-2/'+scm_path]], workspaceUpdater: [$class: 'CheckoutUpdater']])	
		}
		   
		env.SCM_REVISION = revision.replaceAll("@","")
		echo 'Revision number:' + env.SCM_REVISION
		//${env.SCM_REVISION}
				   
		setMavenThreeAndJavaSeven()  
		   
		bat 'mvn clean package -Penv-dev versions:set -DnewVersion=${env.BUILD_NUMBER}'
		bat "zip -D -r src.zip src/ pom.xml"
		//Archive artifact to be used later.
		archive "src.zip"    
		
		bat 'svn copy  --non-interactive -m "branch the current software" http://ncecvsmad02/scm/svn/amadeus/gda-online-2/' + scm_path + ' http://ncecvsmad02/scm/svn/amadeus/gda-online-2/branches/R' + env.BUILD_NUMBER	
		
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
			unarchive mapping: ["src.zip": "."]
			bat 'unzip -o src.zip'
		   
			setMavenThreeAndJavaSeven()
			
			try {			
				bat "mvn clean install -Penv-dev versions:set -DnewVersion=${env.BUILD_NUMBER} -DskipTests=true -Dmaven.test.skip=true"			
			} catch(error) {
			    echo "First deploy failed, let's retry again"
			      
			    retry(5) {				
					def userInput2 = input(message: 'Deploy failed, retry?', ok: 'Continue', 
							parameters: [booleanParam(defaultValue: false, 
							description: 'Check Deploy option if you want to re-deploy this build or Continue if you want to run next stage',name: 'Deploy')])
			
					echo ("userInput2: " + userInput2)
					
					if (userInput2) {
						bat "mvn clean install -Penv-dev versions:set -DnewVersion=${env.BUILD_NUMBER} -DskipTests=true -Dmaven.test.skip=true"
					}
				}
			}
			
			sleep 90
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