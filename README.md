## Chip-0007 Project

### Notice
Update has been made to CSV file to create structured and well formatted data for transformation and processing.

### Description
A tool that takes .csv file name and generate Chip-0007 JSON schema for each line.

It generates a JSON file from the JSON schema and create a SHA-256 checksum for the file.

It then create a file containing each line of the CSV input file and append a the checksum to each row or line.

### Required argument

To run the application successfully with the required inputs and generated output.
You need to provide a command line or CLI argument or parameters which are 2 and includes the .csv file name which should point to a file that exists and a string that will be used to generate the newly csv contained checksum file.

The needed argument as a default is "nft_grit.csv" for arg1 and any valid string as arg2 and this can be a team name.

##### Replace existing CSV provided as input, saved in the file system and use another CSV file of another team to generate and build new CSV with specific checksum or hashes.

There is a nft_grit.csv file which can be replaced with another team CSV with the same structure to get the new CSV generated. After replacing the existing default CSV file, you have to provide the name of the file you save it with as the first argument or arg1 when executing or building the project.

##### Output file name

The output or newly generated .csv file must match "filename.output.csv" and replace "filename" with your team name 

##### Task Description

Take the CSV provided by the teams, and generate a CHIP-0007 compatible json, calculate the sha256 of the json file and append it to each line in the csv (as a filename.output.csv)

##### CSV Input Directory
To have a smooth execution, the input CSV file must be within the root directory outside of src/ folder.

### References
- [Team Grit CSV](https://docs.google.com/spreadsheets/d/1b5H3bp_9-YVjTYQNjLeokXJewrcPfgUo_MYvYXtaUno/edit#gid=0)
- [Chip-0007 JSON Schema Example](https://github.com/Chia-Network/chips/blob/main/assets/chip-0007/example.json)
- [Create file checksum with SHA](https://mkyong.com/java/how-to-generate-a-file-checksum-value-in-java/)
- [JSON Schema Validator](https://www.jsonschemavalidator.net/s/0Aw7Bmlb)

##### Notes:
If you are running the tool from the IDE, you need to enable annotation processing so that lombok will be executed fine.

- [Configure Annotation Processors IntelliJ](https://www.jetbrains.com/help/idea/annotation-processors-support.html#annotation_processing)
- [Setting up Lombok with Eclipse and Intellij](https://www.baeldung.com/lombok-ide)

### Chip-0007 Sample JSON Schema

```
{
    "format": "CHIP-0007",
    "name": "Pikachu",
    "description": "Electric-type Pokémon with stretchy cheeks",
    "minting_tool": "SuperMinter/2.5.2",
    "sensitive_content": false,
    "series_number": 22,
    "series_total": 1000,
    "attributes": [
        {
            "trait_type": "Species",
            "value": "Mouse"
        },
        {
            "trait_type": "Color",
            "value": "Yellow"
        },
        {
            "trait_type": "Friendship",
            "value": 50,
            "min_value": 0,
            "max_value": 255
        }
    ],
    "collection": {
        "name": "Example Pokémon Collection",
        "id": "e43fcfe6-1d5c-4d6e-82da-5de3aa8b3b57",
        "attributes": [
            {
                "type": "description",
                "value": "Example Pokémon Collection is the best Pokémon collection. Get yours today!"
            },
            {
                "type": "icon",
                "value": "https://examplepokemoncollection.com/image/icon.png"
            },
            {
                "type": "banner",
                "value": "https://examplepokemoncollection.com/image/banner.png"
            },
            {
                "type": "twitter",
                "value": "ExamplePokemonCollection"
            },
            {
                "type": "website",
                "value": "https://examplepokemoncollection.com/"
            }
        ]
    },
    "data": {
        "example_data": "VGhpcyBpcyBhbiBleGFtcGxlIG9mIGRhdGEgdGhhdCB5b3UgbWlnaHQgd2FudCB0byBzdG9yZSBpbiB0aGUgZGF0YSBvYmplY3QuIE5GVCBhdHRyaWJ1dGVzIHdoaWNoIGFyZSBub3QgaHVtYW4gcmVhZGFibGUgc2hvdWxkIGJlIHBsYWNlZCB3aXRoaW4gdGhpcyBvYmplY3QsIGFuZCB0aGUgYXR0cmlidXRlcyBhcnJheSB1c2VkIG9ubHkgZm9yIGluZm9ybWF0aW9uIHdoaWNoIGlzIGludGVuZGVkIHRvIGJlIHJlYWQgYnkgdGhlIHVzZXIu"
    }
}
```


