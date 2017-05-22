# Merge

Merging sequences is the operation consisting of listening for values from several
`Publisher`s and emitting them in a single `Flux`.

On this first exercise we will begin by merging elements of two `Flux` as soon as they arrive.
The caveat here is that values from `flux1` arrive with a delay, so in the resulting `Flux`
we start seeing values from `flux2` first. 

@[Simple Merge]({"stubs": ["/src/main/java/io/pivotal/literx/Part05Merge.java"], "command": "io.pivotal.literx.Part05MergeTest#mergeWithInterleave", "layout":"aside"})

But if we want to keep the order of sources, we can use the `concat` operator. Concat will
wait for `flux1` to complete before it can subscribe to `flux2`, ensuring that all the values
from `flux1` have been emitted, thus preserving an order corresponding to the source.

@[Keep the order]({"stubs": ["/src/main/java/io/pivotal/literx/Part05Merge.java"], "command": "io.pivotal.literx.Part05MergeTest#mergeWithNoInterleave", "layout":"aside"})

You can use `concat` with several `Publisher`. For example, you can get two `Mono` and turn
them into a same-order `Flux`:

@[Create a flux from two mono]({"stubs": ["/src/main/java/io/pivotal/literx/Part05Merge.java"], "command": "io.pivotal.literx.Part05MergeTest#multipleMonoToFlux", "layout":"aside"})

