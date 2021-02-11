# jsonTreeChecker
Dev language : Java
Test framework : TestNG
Build framework : Maven

Target:
Implement and cover with functional tests console application which find recursivlly and output count of objects in a "given" json document:
- which contains "specific field" only
- which contains "specific field" and "specific value"

Separate "json tree" travers and condition checkers in separate classes.

Application should consume following arguments (no validation required):
- "given" json document file path
- "specific field"
- "specific value"

For instance, in a following format `java -jar application.jar /home/user/testDocument.json childCount 5` with ouptut similar to:
- found 5 objects with field "childCount"
- found 3 objects where "childCount" equals "5"n
