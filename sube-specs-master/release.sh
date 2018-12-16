
VERSION=$1
docker build -t nicopaez/financialbike:$VERSION .
docker push nicopaez/financialbike:$VERSION
docker tag nicopaez/financialbike:$VERSION nicopaez/financialbike:latest
docker push nicopaez/financialbike:latest 
