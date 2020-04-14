

function createCode(){    
    code = "";    
    var codeLength = 4;//验证码的长度    
    //所有候选组成验证码的字符，可以用中文    
    var selectChar = new Array(2,3,4,5,6,7,8,'A','B','C',  
            'D','E','F','G','H','I','J','K','L','M','N','P',  
            'Q','R','S','T','U','V','W','X','Y','a','b','c',  
            'd','e','f','h','i','j','k','m','n','p',  
            'q','r','s','t','u','v','w','x','y');    
    for(var i=0;i<codeLength;i++){    
        var charIndex = Math.floor(Math.random()*60);    
        code +=selectChar[charIndex];    
    }    
    return code;    
}

