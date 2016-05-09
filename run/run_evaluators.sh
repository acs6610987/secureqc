for i in $(seq 0 $(($1 - 1)))
do
  java -Xmx1536m -cp ../bin/:../lib/*:lib/*:../lib/commons-math3-3.6/* graphsc.parallel.Machine -garblerId $i -garblerPort $((35000 + $i )) -isGen false -inputLength $2 -program $3 -totalGarblers $1 -machineConfigFile $4 -mode $5 -peerBasePort 55000 -offline $6 &
done