Example code for a longevity demo.

Runs out of the box like so:

```scala
sbt run
```

To run with blocking effects:

```scala
sbt runMain blockingApplication
```

Or with `cats.effect.IO`:

```scala
sbt runMain ioApplication
```

Feel free to use the contents of this project however you wish. If you
need me to explicitly license it, please just ask.
