def CONTAINER_NAME="product-service"
def CONTAINER_TAG="latest"
def DOCKER_HUB_USER="20170918"
def HTTP_PORT="8090"

node {

    stage('Initialize'){
        def dockerHome = tool 'myDocker'
        def mavenHome  = tool 'myMaven'
    
        env.PATH = "${dockerHome}/bin:${mavenHome}/bin:${env.PATH}"
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Build'){
        sh "mvn clean install"
    }

    stage('Sonar'){
        try {
            sh "mvn sonar:sonar -Dlogin=admin -Dpassword=admin"
        } catch(error){
            echo "The sonar server could not be reached ${error}"
        }
     }

    stage("Image Prune"){
        imagePrune(CONTAINER_NAME)
    }

    stage('Image Build'){
        imageBuild()
    }

   stage('Push to Docker Registry'){
            withCredentials([usernamePassword(credentialsId: 'dockerHubAccount', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
            pushToImage(CONTAINER_NAME, CONTAINER_TAG, USERNAME, PASSWORD)
            }
        }
   stage('kubernetes set up')
   withKubeConfig([credentialsId: '0c1cca99-ab7e-4865-bc00-3acc7d11c2f5', serverUrl: 'https://192.168.99.102:8443']) {
       try{
       
             sh "kubectl cluster-info"
             sh "kubectl create -f product-service-deployment.yml"
           echo "deployment done.."
       }catch(e){
            echo "something failed kubernetes setup"
           throw e;
       }
             
   }

    stage('Run App'){
    
        environment {
          DYNAMODB_ENDPOINT_URL: "http://192.168.99.100:8000"
          AWS_ACCESS_KEY_ID: "testkey"
          AWS_SECRET_ACCESS_KEY: "testsecretkey"
        }

        runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT)
    }

}

def notify(String message){
    
     slackSend (color: '#FFFF00', message: "${message}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")

    
}

def deploy(username,password){
    sh "docker login -u $dockerUser -p $dockerPassword"
    echo "$dockerUser"
    sh "docker push $dockerUser/$containerName:$tag"
    echo "Image push complete"
}

def imagePrune(containerName){
    try {
        sh "docker image prune -f"
       // sh  "docker stop dynamodb"
        sh "docker stop $containerName"
        echo "Image prune complete"
    } catch(error){}
}

def imageBuild(){
    sh "mvn dockerfile:build"
    echo "Image build complete"
}

def pushToImage(containerName, tag, dockerUser, dockerPassword){
    sh "docker login -u $dockerUser -p $dockerPassword"
    echo "$dockerUser"
    sh "docker push $dockerUser/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPort){
    sh "docker pull $dockerHubUser/$containerName"
  //  sh "kubectl get pods"
    //sh "docker pull amazon/dynamodb-local:latest"
   // sh "docker run -d --rm -p 8000:8000 --name dynamodb amazon/dynamodb-local:latest"
    //  "docker-compose -f docker-compose-tx-user-service.yml up -d"
    //sh "docker run -d --rm -p $httpPort:8080 -e AWS_ACCESS_KEY_ID='testkey' -e AWS_SECRET_ACCESS_KEY='testsecretkey' --name $containerName $dockerHubUser/$containerName:$tag"
    echo "Application started on port: ${httpPort} (http)"
}