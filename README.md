# Huffman coding in java

Compress a text file using Huffman algorithm.

[Subject](http://cedric.cnam.fr/~soutile/SD/Projet_Huffman.pdf)

## Compile and execute

```bash
cd src/
javac *.java
java Main
```

## Usage
 
 ```
Main [-ehuV] -i=<inputFile> -o=<outputFile>

  -i, --input=<inputFile>   Input file.
  -o, --output=<outputFile> Output file.
  -u, --uncompress          Uncompress input file into output file.
  -e, --extra               Print compression rate, input and output file sizes.

  -h, --help                Show this help message and exit.
  -V, --version             Print version information and exit.
 ```

## Exemples

### Compress a file

```
$ java Main -i text_file_to_compress.txt -o  compressed_text_file.huff
```

### Uncompress a file
Use -u to activate uncompress mode.

```
$ java Main -u -i compressed_text_file.huff -o uncompressed_text_file.txt
```

### Print extras
Use -e to print extras : characters (ascii value, frequency, Huffman code) and compression gain.
```
$ java Main -e -i text_file_to_compress.txt -o  compressed_text_file.huff
$ java Main -ue -i compressed_text_file.huff -o uncompressed_text_file.txt
```