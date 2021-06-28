.PHONY: push


# gcr.io/<project>/<image_name>
push:
	@docker build -f ./cloud.Dockerfile -t gcr.io/reliable-messaging/cloud-component .
	@docker push gcr.io/reliable-messaging/cloud-component