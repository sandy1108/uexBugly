var accommon = {
    callbackArray:[],
    ready:function(callback){
        this.callbackArray.push(callback);
    }
}

function checkDataValid(realData, rightData){
    var errorInfo;
    var count = 0;
    for (let dataKey in rightData) {
        if(rightData.hasOwnProperty(dataKey)){
            if(!realData.hasOwnProperty(dataKey)){
                errorInfo += "\n 缺失字段：" + dataKey;
            }
            count++;
        }
    }
    errorInfo += "\n 共检查了 "+count+" 个字段"
    return errorInfo;
}

window.uexOnload = function(){
    for (var index = 0; index < accommon.callbackArray.length; index++) {
        var callback = accommon.callbackArray[index];
        (callback)();
    }
}