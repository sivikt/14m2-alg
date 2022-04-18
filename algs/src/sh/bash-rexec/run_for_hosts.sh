#!/bin/bash

##
# Reads <hosts_file> where each line is a host name or IP address
# and executes <commands_shell_file> passing read line as a first 
# parameter.
# 
# Usage: sh <script.sh> <hosts_file> <commands_shell_file> [--yes] [--run-on-local] [--params param1 param2 ...]
##

declare args=("$@")

declare hosts_file
declare exec_cmd_file
declare force_local_exec=false
declare always_agree=false
declare -a parameters

declare local_ip=`ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p'`

ParseArgs()
{
    if [ -z "${args[0]}" ]; then
       echo "Specify hosts file"
       exit 1
    fi

    if [ -z "${args[1]}" ]; then
       echo "Specify cmd file"
       exit 1
    fi

    hosts_file=${args[0]}
    exec_cmd_file=${args[1]}

    local next_arg=2

    if [[ ${args[$next_arg]} == "--yes" ]]; then
        next_arg=$[next_arg + 1]
        always_agree=true
    fi

    if [[ ${args[$next_arg]} == "--run-on-local" ]]; then
        next_arg=$[next_arg + 1]
        force_local_exec=true
    fi

    if [[ ${args[$next_arg]} == "--params" ]]; then
        next_arg=$[next_arg + 1]

        for ((i=$next_arg; i < ${#args[@]}; i++)); do
            parameters[$i]=${args[$i]}
        done
    fi

    echo "Input Parameter:"
    echo "hosts source: $hosts_file"
    echo "exec file: $exec_cmd_file"
    echo "always agree with prompt: $always_agree"
    echo "exec file on local host: $force_local_exec"
    echo -e "command extra params: ${parameters[*]}\n"
}

PrintExecCmd()
{
    echo "Execution script body:"
    echo -e ">>> START\n"
    cat $exec_cmd_file
    echo -e "\n<<< END\n"
}

PromptExecution()
{
    if [ $always_agree == true ]; then
       return
    fi

    read -p "Continue execution? (yes|no) "
    echo # empty line
    if [[ $REPLY != "yes" ]]; then
        echo -e "\nAbort execution"
        exit 0
    fi
}

ExecLoop()
{
    while read line
    do
        local host_args=(${line//;/ })
        local target_ip=${host_args[0]}

        if [[ $target_ip == $local_ip ]]; then
            echo -e "Host $target_ip is a local machine!\n"

            if [[ $force_local_exec != "--run-on-local" ]]; then
                echo -e "Skip execution for local...\n"
                continue
            fi
        fi

        local cmd_args=`echo ${host_args[*]} ${parameters[*]}`
        echo "Run on host '$target_ip'. Pass args: '$cmd_args'"

        bash $exec_cmd_file $cmd_args

        case "$?" in
           0)
              echo -e "SUCCESS\n"
              ;;
           1)
              echo -e "FAILED\n"
              exit 1
              ;;
        esac
    done < $hosts_file
}

ParseArgs
PrintExecCmd
PromptExecution
ExecLoop
