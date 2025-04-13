const express = require("express");
const path = require("path");
const app = express();
// External package
const CryptoJS = require('crypto-js');
// Node.js built in crypto package
const crypto = require('crypto');

function encrypt(type, toEncrypt, userKey)
{
  switch(type)
  {
    case "DES":
      const DEStext = CryptoJS.DES.encrypt(toEncrypt, userKey).ciphertext.toString(CryptoJS.enc.Hex);
      return [DEStext, 27.47];
    case "Caeser Cipher":
      return [rot13(toEncrypt), "No Key Used", 0];
    case "AES":
      const AEStext = CryptoJS.DES.encrypt(toEncrypt, userKey).ciphertext.toString(CryptoJS.enc.Hex);
      return [AEStext, userKey, 1000000];
    case "MD5":
      const MD5text = crypto.createHash('md5').update(toEncrypt).digest('hex');
      return [MD5text, "No Key Used",1]
  }
}

function range(text)
{
  var lowers = false;
  var uppers = false;
  var numbers = false;
  var symbols = false;

  var totRange = 0;
  for (var i = 0; i < text.length; i++)
  {
    var c = text.charCodeAt(i);
    if       (c >= 97 && c <= 122) lowers = true;
    else if  (c >= 65 && c <= 90) uppers = true;
    else if  (c >= 48 && c <= 57) numbers = true;
    else symbols = true;
  }

  if (lowers) totRange+=26;
  if (uppers) totRange+=26;
  if (numbers) totRange+=10;
  if (symbols) totRange+=30;

  return totRange;
}


//Tweaked version of https://stackoverflow.com/questions/8981296/rot-13-function-in-java by user georgiecasey
function rot13(toEncrypt)
{
  var sb = "";
  //Builds string on a stringbuilder instance one character at a time and shifts the location of each char by 13 for lowercase and captial
  for (var i = 0; i < toEncrypt.length; i++) 
  {
    var c = toEncrypt.charCodeAt(i);
    if       (c >= 97 && c <= 109) c += 13;
    else if  (c >= 65 && c <= 77) c += 13;
    else if  (c >= 110 && c <= 122) c -= 13;
    else if  (c >= 78 && c <= 90) c -= 13;
    sb+=String.fromCharCode(c);
  }
  return sb.toString();
}

app.get("/submit", (req, res) => {
  var text = req.query.text;
  const key = req.query.key;
  const method = req.query.method;
  //Call method to encrypt
  var encryptedResponse = encrypt(method, text, key);
  var entropy = Math.ceil(text.length * Math.log2(range(text)));
  //TO DO: Add math to create drude metric for relative strenth utilizing entropy and hardness factor, then transfer to a string based on toughness (i.e one computer, nation-state, etc.)

  e = new EncryptedEntry(text,encryptedResponse[0],method,encryptedResponse[1],entropy,encryptedResponse[2],strength);
  const ret = JSON.stringify(e);
  res.end(ret);
});

class EncryptedEntry {
    constructor(plaintext, encryptedText, method, key, entropy, factor, strength) {
      this.plaintext = plaintext;
      this.encryptedText = encryptedText;
      this.method = method;
      this.key = key;
      this.entropy = entropy;
      this.factor = factor;
      this.strength = strength;
    }
  }

app.use(express.static(
  path.resolve(__dirname, "public")
));

app.listen(3000);