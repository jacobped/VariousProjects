#!/bin/bash

configFile="backupIngest.cfg"

processFile() {
    local file=${1}

    # Checks file matches pattern: /some/file/path/file-name-2018-12-31-23-59-59.type
    local baseFileNameFormat="^[a-zA-Z0-9\/\-]*[a-zA-Z0-9-]*-[0-9]{4}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}.[.a-zA-Z0-9\/]*$"

    # Gateway
    if [[ $file =~ ^[a-zA-Z0-9\/\-]*gateway-config-[0-9]{4}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}.xml$ ]]; then
        movefile "${file}" "${gatewayDirName}"

    # Webserver
    elif [[ $file =~ ^[a-zA-Z0-9\/\-]*webserver-[0-9]{4}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}-[0-9]{2}.tar.gz$ ]]; then
        movefile "${file}" "${webserverDirName}"

    elif ! [[ $file =~ $baseFileNameFormat ]]; then
        notify "Skipping unsupported filename pattern: ${file}"

    else
        notify "Skipping filename with no defined move rule: ${file}"
    fi
}

movefile() {
    local file=${1}
    local moveSubDir=${2}

    # Filename, including its extension.
    local filename=$(basename -- "$file")

    # Based on the baseFileNameFormat but adapted for the "sed" tool, and extraction of the year.
    local getYearSedRegex="s/^[a-zA-Z0-9\/\-]*[a-zA-Z0-9-]*-\([0-9]\{4\}\)-[0-9]\{2\}-[0-9]\{2\}-[0-9]\{2\}-[0-9]\{2\}-[0-9]\{2\}.[.a-zA-Z0-9\/]*$/\1/"
    local year="$(echo "${filename}" | sed "${getYearSedRegex}")"

    local resultDir="${backupsDir}/${moveSubDir}/${year}"
    local resultFile="${resultDir}/${filename}"

    mkdir -p "${resultDir}"

    # Check if file already exists. We don't want to overwrite anything.
    if ! [[ -f "${resultFile}" ]]; then
        # Move file to backup dir inside given subfolder.
        mv "${file}" "${resultFile}" || onError "unexpected faillure moving file: ${file}"
        notify "Moved file: ${filename}"
    else
        onError "Can't move file to backup sub dir. File already exists: ${resultFile}"
    fi
}

processIngestDir() {
    if ! [[ -z "$(ls -A ${ingestDir})" ]]; then
        for file in "${ingestDir}"/*; do
            # echo "file: ${file}"
            processFile "${file}"
        done
    else
        log "Ingest dir is empty. Nothing to do."
    fi
}

validateFolders() {
    if [ ! -d "${ingestDir}" ]; then
        onError "ingest directory doesn't exists"
        exit 1
    fi

    if [ ! -d "${backupsDir}" ]; then
        onError "backups directory doesn't exists"
        exit 1
    fi

    # Make sure the paths doesn't end with /
    if [[ "${ingestDir}" == *\/ ]]; then
        onError "ingest directory path ends with forbidden character: /"
        exit 1
    fi

    if [[ "${backupsDir}" == *\/ ]]; then
        onError "backups directory path ends with forbidden character: /"
        exit 1
    fi

    # notify "Given directories validated successfully"
}

doChangePathToScriptDir() {
    # directory location where this script resides.
    local scriptDir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"

    cd "${scriptDir}"
}

doLoadConfig() {
    # Loads config file.
    source backupIngest.cfg
}

#
# Discord Webhook notifier
#
notifyDiscord() {
    if [ -n "${discordWebhookURL}" ]; then
        local msg="$(echo -n "${1}" | tr '\n' '\001' | sed 's/\001/\\n/g;s/["\\]/\\\0/g')"
        local json="{\"content\":\"${msg}\"}"
        curl -s -H "Content-Type: application/json" --data "${json}" "$discordWebhookURL" >/dev/null
    fi
}

#
# Slack Webhook notifier
#
notifySlack() {
    if [ -n "${slackWebhookURL}" ]; then
        local msg="$(echo -n "${1}" | sed 's/"/\"/g' | sed "s/'/\'/g")"
        local json="{\"text\":\"${msg}\"}"
        curl -s -X POST -H "Content-Type: application/json" --data "${json}" "$slackWebhookURL" >/dev/null
    fi
}

log() {
    echo "${1}"
}

notify() {
    log "${1}"
    local msg="*Backup ingest script:* ${1}"
    notifyDiscord "${msg}"
    notifySlack "${msg}"
}

onError() {
    notify "\`ERROR:\` ${1}"
}

main() {
    log "Running backup sript on ingest folder"
    doChangePathToScriptDir
    doLoadConfig
    validateFolders
    processIngestDir
}

# Runs main function. Which runs the script.
main
