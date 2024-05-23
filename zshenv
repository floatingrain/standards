# 用户可执行文件
if ! [[ "$PATH" =~ "$HOME/.local/bin:" ]]; then
    PATH="$HOME/.local/bin:$PATH"
fi
# 代理
export https_proxy=http://localhost:1080
export http_proxy=http://localhost:1080
export all_proxy=socks5h://localhost:1080
export no_proxy=localhost,127.0.0.1
# Rust
export CARGO_HOME="$HOME/ToolChains/Rust/cargo"
export RUSTUP_HOME="$HOME/ToolChains/Rust/rustup"
source "$HOME/ToolChains/Rust/cargo/env"
# Flutter
export PUB_HOSTED_URL="https://pub.flutter-io.cn"
export FLUTTER_STORAGE_BASE_URL="https://storage.flutter-io.cn"
PATH="$HOME/ToolChains/Flutter/bin:$PATH"
# Android
export ANDROID_HOME="$HOME/ToolChains/Android"
ANDROID_TOOLS_BIN="$ANDROID_HOME/platform-tools"
ANDROID_CMDLINE_TOOLS_BIN="$ANDROID_HOME/cmdline-tools/latest/bin"
PATH="$ANDROID_TOOLS_BIN:$ANDROID_CMDLINE_TOOLS_BIN:$PATH"
# Java
export JAVA_HOME="$HOME/ToolChains/GraalVMJDK-17"
PATH="$JAVA_HOME/bin:$PATH"
# Python
export PIPENV_VENV_IN_PROJECT=1
# PATH
export PATH