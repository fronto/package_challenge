# Package Challenge

## Building and running

To build the application run `mvn clean install`

To see the packer in action have a look at run `loadInputFileAndRunPacker()` in the class `EndToEndTest`


## My general algorithm is as follows:

1. For each input line, generate all potential combinations of input items using recursion
2. Check the net weight of each combination and drop it if it is outside the weight limit
3. For the remaining sets of items sort them based on net cost
4. Take the most expensive set(s) of items and choose lightest if there is more than one

## Development notes

Admittedly there are further optimizations that are still possible. For example calculating 
the weight and cost as part and parcel of the recursive generation of combinations of items,
would be a way of reducing and re-using computations. Perhaps this is necessary for larger
input sets? All combinations will still have to be computed, but doing it "on the fly"
as part of generating the combinations should provide opportunities for re-use. Please let
me know if you want me to introduce this...

Although no SLA was specified I did implement an end-to-end test with a 2s timout on an input
 row with the upper bound of 15 items to make sure that the computations take place
 in a reasonable amount of time. On my hardware the row was processed in about 200ms, therefore
 2s is a very generous timeout that should work on most machines.

With the above stated benchmark in mind I decided not to compromise the simplicity of the
 algorithm to squeeze out some extra performance.

Generally I do not comment my code that much, as I believe one should only comment what the
code cannot say. For the most part I try to use meaningful variable, method and class names.
I also believe that unit tests are a way of documenting requirements and therefore should be
easy to read and understand.

On the subject of validation I did not enforce a maximum of 15 items as the constraint mentioned
there could be up to 15 items but did not stipulate this number as a hard maximum per se.

