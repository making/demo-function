```
policy=`cat <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
     {
       "Action": "sts:AssumeRole",
       "Principal": {
         "Service": "lambda.amazonaws.com"
        },
        "Effect": "Allow",
        "Sid": ""
     }
  ]
}
EOF`
aws iam create-role --role-name demo-fn --assume-role-policy-document "$policy"
role=`aws iam get-role --role-name demo-fn --query Role.Arn --output text`
```

```
aws lambda create-function \
    --function-name demo-fn \
    --role $role \
    --zip-file fileb://$(pwd)/target/demo-function-0.0.1-SNAPSHOT-aws.jar \
    --handler com.example.demofunction.LambdaHandler \
    --description "Demo Fn" \
    --runtime java8 \
    --region ap-northeast-1 \
    --timeout 30 \
    --memory-size 300 \
    --publish
    
functionArn=`aws lambda get-function --function-name demo-fn --query=Configuration.FunctionArn --output text`
```

```
aws lambda update-function-code --function-name demo-fn --zip-file fileb://$(pwd)/target/demo-function-0.0.1-SNAPSHOT-aws.jar
```


```
aws apigateway create-rest-api --name demo-fn-api
apiId=`aws apigateway get-rest-apis --query="items[?name == 'demo-fn-api'].id" --output text`
resourceId=`aws apigateway get-resources --rest-api-id $apiId --query="items[?path == '/'].id" --output text`
    
aws apigateway put-method \
    --rest-api-id $apiId \
    --resource-id $resourceId \
    --http-method POST \
    --authorization-type NONE
```


```
aws apigateway put-integration \
   --rest-api-id $apiId \
   --resource-id $resourceId \
   --http-method POST \
   --type AWS \
   --integration-http-method POST \
   --uri arn:aws:apigateway:ap-northeast-1:lambda:path/2015-03-31/functions/$functionArn/invocations
```


```
aws apigateway put-integration-response \
   --rest-api-id $apiId \
   --resource-id $resourceId \
   --http-method POST \
   --status-code 200 \
   --response-templates "{\"text/plain\": \"\$input.path('$')\"}"
```

```
aws apigateway put-method-response \
   --rest-api-id $apiId \
   --resource-id $resourceId \
   --http-method POST \
   --status-code 200 \
   --response-models "{\"text/plain\": \"Empty\"}"
```

```
aws apigateway create-deployment \
    --rest-api-id $apiId \
    --stage-name prod
```


```
accountId=`echo $functionArn | cut -d ':' -f 5`
statementId=`uuidgen | tr [:upper:] [:lower:]`
# d88e71c9-99d9-4334-9721-8979ad976f5a

aws lambda add-permission \
   --function-name demo-fn \
   --statement-id $statementId \
   --action lambda:InvokeFunction \
   --principal apigateway.amazonaws.com \
   --source-arn arn:aws:execute-api:ap-northeast-1:$accountId:$apiId/*/POST/
```