# QBS: Git commit-msg hook installer

# Variables
PROJECT_PREFIX=$1
FILE_NAMES=()

# Constants
GIT_HOOKS_DIR=".git/hooks/"
SCRIPT_DIR="git-hooks/"
INSTALL_SCRIPT_NAME="install.sh"
REMOTE_INSTALL_SCRIPT_ADDRESS="https://gitlab.com/snippets/1883936/raw"
REMOTE_VERSION_ADDRESS="https://gitlab.com/snippets/1883954/raw"
GIT_HOOKS=( "commit-msg;https://gitlab.com/snippets/1883937/raw" "pre-push;https://gitlab.com/snippets/1883934/raw" )

# Regex
PROJECT_PREFIX_REGEX="^[A-Z]{2,4}$"

## INTIAL SETUP

if [[ -n $PROJECT_PREFIX ]] || [[ -z $(git config --local project.qbs.prefix) ]]; then
  if [[ -z $(echo ${PROJECT_PREFIX} | grep -E $PROJECT_PREFIX_REGEX) ]]; then
    echo "\033[0;32m\nProject Configuration\033[0m"
    echo 'What is the Project Prefix for your project? Typically used in e.g. Jira and consist of 2-4 Uppercase Characters.'
    read PROJECT_PREFIX
    if [[ -z $(echo ${PROJECT_PREFIX} | grep -E $PROJECT_PREFIX_REGEX) ]]; then
      echo "\033[0;31mProject Prefix not correct. Needs to be 2-4 Uppercase Characters. Please start again and specify as first parameter.\033[0m"
      exit
    fi
  fi
  
  echo "Setting Project Prefix to $PROJECT_PREFIX"
  echo "Executing: git config --local project.qbs.prefix $PROJECT_PREFIX"
  git config --local project.qbs.prefix $PROJECT_PREFIX
  echo "Creating Script Folder: mkdir $SCRIPT_DIR"
  mkdir $SCRIPT_DIR
  echo "Resetting QBS Git Hook Version"
  git config --local project.qbs.hookversion "-"
fi

PROJECT_PREFIX=$(git config --local project.qbs.prefix)

## UPDATE SCRIPTS

LOCAL_VERSION="$(git config --local project.qbs.hookversion)"
REMOTE_VERSION="$(curl -sS $REMOTE_VERSION_ADDRESS)"

echo "\nLocal QBS Git Hook Version: $LOCAL_VERSION"
echo "Remote QBS Git Hook Version: $REMOTE_VERSION"

if [[ $LOCAL_VERSION != $REMOTE_VERSION ]]; then
  echo "\n\033[0;32mUpdating Git Hooks from Remote\033[0m"
  echo "Executing: curl -sS $REMOTE_INSTALL_SCRIPT_ADDRESS > $SCRIPT_DIR$INSTALL_SCRIPT_NAME"
  curl -sS $REMOTE_INSTALL_SCRIPT_ADDRESS > $SCRIPT_DIR$INSTALL_SCRIPT_NAME
  for HOOK in ${GIT_HOOKS[*]}; do
    FILE_NAME="${HOOK%%;*}"
    REMOTE_ADDRESS="${HOOK##*;}"
    echo "Executing: curl -sS $REMOTE_ADDRESS > $SCRIPT_DIR$FILE_NAME"
    curl -sS $REMOTE_ADDRESS > $SCRIPT_DIR$FILE_NAME
  done
  git config --local project.qbs.hookversion $REMOTE_VERSION
fi

## INSTALL HOOKS

for HOOK in ${GIT_HOOKS[*]}; do
  FILE_NAME="${HOOK%%;*}"
  FILE_NAMES+=("$FILE_NAME")
done

for FILE_NAME in ${FILE_NAMES[*]}
do
  # Checking differnce between the file inside .git/hooks and the file in our repository
  # -N to not fail when one file does not exists
  difference="$difference$(diff -N $SCRIPT_DIR$FILE_NAME $GIT_HOOKS_DIR$FILE_NAME)"
done

# if difference is empty, then both files are identical
if [[ -z $difference ]]; then
  echo "Git Hook Up-to-date"
else
  echo "Install git-hook started"
  rm -rf "$GIT_HOOKS_DIR"

  # creating hooks dir if it does not already exist (git init creates it)
  git init

  for FILE_NAME in ${FILE_NAMES[*]}
  do
    # copying script, making it executable and running git init (so that changes are applied)
    cp $SCRIPT_DIR$FILE_NAME $GIT_HOOKS_DIR
    chmod +x "$GIT_HOOKS_DIR$FILE_NAME"
  done
  git init

  echo "Install git-hook finished"
fi
