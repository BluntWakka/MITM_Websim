
function init() {
}

$( () => {
  init();
});

function encrypt(type, toEncrypt, userKey)
{
  switch(type)
  {
    case "-D":
    case "-d":
      const DEStext = CryptoJS.DES.encrypt(toEncyrpt, userKey).toString(CryptoJS.enc.Hex);
      return DEStext;
    case "-C":
    case "-c":
      return rot13(toEncrypt);
    case "-A":
    case "-a":
      const AEStext = CryptoJS.DES.encrypt(toEncyrpt, userKey).toString(CryptoJS.enc.Hex);
      return AEStext;
  }
}


//Tweaked version of https://stackoverflow.com/questions/8981296/rot-13-function-in-java by user georgiecasey
function rot13(toEncrypt)
{
  var sb = "";
  //Builds string on a stringbuilder instance one character at a time and shifts the location of each char by 13 for lowercase and captial
  for (var i = 0; i < toEncrypt.length(); i++) 
  {
    var c = input.charAt(i);
    if       (c >= 'a' && c <= 'm') c += 13;
    else if  (c >= 'A' && c <= 'M') c += 13;
    else if  (c >= 'n' && c <= 'z') c -= 13;
    else if  (c >= 'N' && c <= 'Z') c -= 13;
    sb+=c;
  }
  return sb.toString();
}