## Team Bevel Chip-0007 Project

### Description
A tool that takes .csv file name and generate Chip-0007 JSON schema for each line.

It generates a JSON file from the JSON schema and create a SHA-256 checksum for the file.

It then create a file containing each line of the CSV input file and append a the checksum to each row or line.

### Required argument

To run the application successfully with the required inputs and generated output.
You need to provide a command line or CLI argument or parameters which are 2 and includes the .csv file name which should point to a file that exists and a string that will be used to generate the newly csv contained checksum file.

The needed argument as a default is "nft_bevel.csv" for arg1 and any valid string as arg2.

### Replace existing CSV provided as input.

There is a nft_bevel file which can be replaced with another team CSV with the same structure to get the new CSV generated.

### Task Description

Take the CSV provided by the teams, and generate a CHIP-0007 compatible json, calculate the sha256 of the json file and append it to each line in the csv (as a filename.output.csv)

### References
- [Team Bevel CSV](https://docs.google.com/spreadsheets/d/1-9zf50iUmdtvpbEvQ7I-M2vlK8hCSB1DC_bF5bDNjxE/edit#gid=663257143)
- [Chip-0007 JSON Schema Example](https://github.com/Chia-Network/chips/blob/main/assets/chip-0007/example.json)

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


