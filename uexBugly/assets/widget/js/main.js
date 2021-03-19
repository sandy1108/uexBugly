function openNewWin(inWndName,html,inAniID,f,time)
{
    f = 1024;
    if (inAniID) {
        uexWindow.open(inWndName,'0',html,inAniID,'','',(f)?f:4,time?time:250);
    }
    else {
        uexWindow.open(inWndName, '0', html, '2', '', '', (f) ? f : 4);
    }
}

function openTest1(name){
    uexWindow.open(name, '0', name+".html", '2', '', '', 0);
}

function closeWin()
{
    uexWindow.close(-1);
}
