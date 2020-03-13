FROM circleci/android:api-29-node

RUN sudo apt-get update \
 && sudo apt-get install chromium -y \
 && sudo rm -rf /var/lib/apt/lists/*

