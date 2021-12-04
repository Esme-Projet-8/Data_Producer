PLANT_UML_PATH="plantuml.jar"
PLANT_UML_SRC_FOLDER="diagrams/src"
PLANT_UML_OUTPUT_FOLDER="diagrams/output"

for file in "$PLANT_UML_SRC_FOLDER"/*
do
  if [ $file != "img/src/includes.wsd" ]; then
    java -DPLANTUML_LIMIT_SIZE=10000 -jar $PLANT_UML_PATH $file
    FILE_NAME_NO_EXTENSION="$(cut -d'.' -f1 <<<"$file")"
    mv "$FILE_NAME_NO_EXTENSION.png" $PLANT_UML_OUTPUT_FOLDER
  fi
done