instanceName="xapiAccount"
copyFile="/tmp/xapi.sh"
fileName="$(basename "$0")"
qualifiedPath="$(dirname "$(readlink -f "$0")")/$fileName"

function killInstance {
    echo "[INFO] Killing previous instance"
    pkill -f "$instanceName"
    if [[ $! -eq 0 ]]
    then echo "[OK] Instance killed"; 
    else echo "[FAIL] No instance to kill"; 
    fi
}
function runInstance {
    echo "[INFO] Running instance" >> /tmp/xapi.instance.log
    exec -a "$instanceName" java -jar ./target/xapi-account-0.0.1-SNAPSHOT.jar 1>> /tmp/xapi.instance.log 2>> /tmp/xapi.instance.log &
    echo "[OK] Async process launched" >> /tmp/xapi.instance.log
}

if [[ "$qualifiedPath" != "$copyFile" ]]
then
    echo "[INFO] Redirecting to $copyFile $@"
    cp -f "$qualifiedPath" "$copyFile"
    exec bash $copyFile $@
else
    echo "[INFO] Running $fileName $@"
    for param in "$@"
    do
        if [[ "$param" = "-k" ]]; then killInstance; fi
        if [[ "$param" = "-r" ]]; then runInstance; fi
    done
fi