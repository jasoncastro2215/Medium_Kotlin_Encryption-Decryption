package encryptdecrypt

import java.io.File
/*
shifting algorithm — it shifts each letter by the specified number according to its order in the alphabet. The second one is based on the Unicode table, like in the previous stage.

Objectives
When starting the program, the necessary algorithm should be specified by an argument (-alg). Name the first algorithm as shift, the second one as unicode. If there is no -alg argument, default it to shift.

Remember that in case of shift, encode only English letters — from "a" to "z" and from "A" to "Z". In other words, after "z" comes "a", after "Z" comes "A".

Examples
Example 1: reading and writing to files; the arguments are: -mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode

This command must get data from road_to_treasure.txt, encrypt the data with the key of 5, create protected.txt, and write ciphertext into it.

Example 2: encryption with the unicode algorithm; the arguments are: -mode enc -key 5 -data "Welcome to hyperskill!" -alg unicode

\jqhtrj%yt%m~ujwxpnqq&
Example 3: decryption with the unicode algorithm; the arguments are: -key 5 -alg unicode -data "\jqhtrj%yt%m~ujwxpnqq&" -mode dec

Welcome to hyperskill!
Example 4: encryption with the shift algorithm; the arguments are: -key 5 -alg shift -data "Welcome to hyperskill!" -mode enc

Bjqhtrj yt mdujwxpnqq!
Example 5: decryption with the shift algorithm; the arguments are: -key 5 -alg shift -data "Bjqhtrj yt mdujwxpnqq!" -mode dec

Welcome to hyperskill!
 */
fun main(args: Array<String>) {
    var alg = "shift"
    var mode = "enc"
    var data = ""
    var key = 0
    var fileOut = ""
    var fileIn = ""
    var output = ""
    for (i in args.indices step 2) {
        when (args[i]) {
            "-alg" -> alg = args[i + 1]
            "-mode" -> mode = args[i + 1]
            "-data" -> data = args[i + 1]
            "-key" -> key = args[i + 1].toInt()
            "-out" -> fileOut = args[i + 1]
            "-in" -> fileIn = args[i + 1]
        }
    }

    val file = File(fileIn)
    for (c in (data.ifEmpty { if (file.exists()) file.readText() else "" }).chars()) {
        val isUppercase = c.toInt() in 65..90
        when (alg) {
            "unicode" -> output += (c + (key * if (mode == "enc") 1 else -1)).toChar()
            "shift" -> output += if (c.toChar().toString().lowercase().toCharArray()[0].code in 97..122) {
                val isEnc = mode == "enc"
                val operator = if (isEnc) 1 else -1
                val diff = c.toChar().toString().lowercase().toCharArray()[0].code + operator * key - (if (isEnc) 122 else 97)
                var char =
                    if (if (isEnc) diff > 0 else diff < 0) (diff + (if (isEnc) 'a' else 'z').code + -operator * 1).toChar() else (c.toChar().toString().lowercase().toCharArray()[0] + operator * key)
                if (isUppercase) char.toString().uppercase().toCharArray()[0] else char
            } else
                c.toChar()
        }
    }
        if (fileOut.isEmpty())
            print(output)
        else
            File(fileOut).writeText(output)
    }