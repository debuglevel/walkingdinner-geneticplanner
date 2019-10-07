# this runs the jar and pipes the input file as STDIN into it; the result is retrieved via STDOUT

# XXX: if the MainClassName in the jar changes, it may be defined this way:
# java -cp ../../build/libs/planners-0.0.2-SNAPSHOT-all.jar io.micronaut.function.executor.FunctionApplication

cat ../input.json | java -jar ../../build/libs/planners-0.0.2-SNAPSHOT-all.jar
