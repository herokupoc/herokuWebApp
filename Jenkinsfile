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