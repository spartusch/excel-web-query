#!/bin/bash

declare $(env -i `cat bin/configuration.vars`)

echo
echo "=> Starting to build image '$project_group/$project_name':latest (might take a while) ..."
docker image build -t $project_group'/'$project_name':latest' .
echo

echo "=> Done"
echo
