#!/bin/bash

# Update package list and install prerequisites
echo "Updating package list and installing prerequisites..."
sudo apt-get update
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common

# Add Docker's official GPG key
echo "Adding Docker's official GPG key..."
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

# Set up the stable Docker repository
echo "Setting up the Docker stable repository..."
sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"

# Install Docker
echo "Installing Docker..."
sudo apt-get update
sudo apt-get install -y docker-ce

# Check Docker installation
echo "Checking Docker installation..."
sudo docker --version
if [ $? -ne 0 ]; then
    echo "Docker installation failed."
    exit 1
fi

# Install Docker Compose
echo "Installing Docker Compose..."
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose

# Check Docker Compose installation
echo "Checking Docker Compose installation..."
docker-compose --version
if [ $? -ne 0 ]; then
    echo "Docker Compose installation failed."
    exit 1
fi

# Ensure docker service is running
echo "Starting Docker service..."
sudo systemctl start docker
sudo systemctl enable docker

# Clone the project repository (Replace with actual repo URL)
# git clone https://github.com/your-repo/project-name.git
# cd project-name

# Run Docker Compose for the project
echo "Running Docker Compose..."
docker-compose up --build -d

# Check if the services are running
if [ $? -eq 0 ]; then
    echo "Docker Compose services are up and running."
else
    echo "Failed to run Docker Compose."
fi
