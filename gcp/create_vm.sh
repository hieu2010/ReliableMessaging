#!/bin/sh

INSTANCE_NAME="cloud-node"
TAG="reliable-messaging"
TIME_ZONE="europe-west1-b"

# Assume that a public-private key pair has been already created
# Furthermore, assume KEY_FILENAME=rel-msg-key
USERNAME=$(cat ~/.ssh/rel-msg-key.pub | awk '{print $3}')

# Adjustment for the google cloud format: 'USERNAME: ssh-rsa KEY_VALUE USERNAME'
echo "${USERNAME}:$(cat ~/.ssh/rel-msg-key.pub)" > ./rel-msg-key-gcp-format.pub

# Add the public key to the project's meta
gcloud compute project-info add-metadata --metadata ssh-keys="$(cat ./id_rsa_gcp.pub)"

# Create a firewall rule
gcloud compute firewall-rules create "cloud-node-rule" \
--allow tcp:22,tcp:5054,tcp:5055,udp:123,icmp \
--direction=INGRESS \
--target-tags=$TAG

# Create an instance
 gcloud compute instances create $INSTANCE_NAME \
 --image-family ubuntu-1804-lts \
 --image-project ubuntu-os-cloud \
 --machine-type=e2-standard-2 \
 --tags=$TAG \
 --zone=$TIME_ZONE

 # Save public ip in a var
 PUBLIC_IP=$(gcloud compute instances describe $INSTANCE_NAME \
--zone="europe-west1-b" \
--format='get(networkInterfaces[0].accessConfigs[0].natIP)')

# Add the vm to the known hosts
ssh-keyscan -H $PUBLIC_IP >> ~/.ssh/known_hosts

# A simple test
ssh -i ~/.ssh/rel-msg-key ${USERNAME}@$PUBLIC_IP 'pwd'

