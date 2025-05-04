Idea of project is to encrypt a user provided piece of data with different encryption schemes and 
simulate what an attacker could do if they intercepted. Along with the ciphertext produced from the scheme, some extra added information about entropy and realistically who or what could crack the password in a reasonable span of time is given.

Essentially, this project is a roundabout way of testing various encryption schemes for security
given only the ciphertext (and maybe public key if it is pertinent to the scheme)

Currently want to include JavaScript that utilizes CSS and HTML for formatting

Previously made java code that goes through some of the encryption options present in the javascript will be included in the repository for the sake of comparison of ease (unused in web build, but still what I believe to be an interesting resource).

Metrics that hardness factors were discorvered from the following (https://asecuritysite.com/blog/2023-07-04_Those-Tables-Password-Cracking-Times-That-Scare-You----Are-Mostly-Wrong--7d03bf4aec6.html). Following source of image to the HashCat website gives a far more detialed listing of the average hashes per second computed by seperate GPUs (https://openbenchmarking.org/test/pts/hashcat-1.1.1). Not all hardness should be taken as exact since acutal computation time varies system to system, but this serves as a fair estimate. Factor is based on MD5 (saltless) as a base.