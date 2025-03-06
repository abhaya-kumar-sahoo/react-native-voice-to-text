cmake_minimum_required(VERSION 3.4.1)
project(VoiceToText)

set (CMAKE_VERBOSE_MAKEFILE ON)
set (CMAKE_CXX_STANDARD 14)

add_library(react-native-voice-to-text            SHARED
            ../cpp/react-native-voice-to-text.cpp
            cpp-adapter.cpp
)

# Specifies a path to native header files.
include_directories(
            ../cpp
)
