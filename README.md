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



