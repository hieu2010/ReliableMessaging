.PHONY: push

push:
	@docker build -f ./cloud.Dockerfile -t gcr.io/reliable-messaging/cloud-component .
	@docker push gcr.io/reliable-messaging/cloud-component