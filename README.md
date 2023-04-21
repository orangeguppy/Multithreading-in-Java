# Multithreading-in-Java

Consider a sandwich making factory with three types of machines: a bread toaster, a scrambled egg maker, and a sandwich packer
  - A bread toaster will toast a single slice of bread and put it in a bread pool
  - A scrambled egg maker will make a single portion of scrambled egg and put it in the 
    scrambled egg pool
  - A sandwich packer takes one slice of bread from bread pool, then one portion of 
    scrambled egg from the scrambled egg pool, and finally take one more slice of bread 
    from the bread pool, and then pack the sandwich.

The factory has multiple bread toasters, multiple scrambled egg makers and multiple sandwich packers working concurrently. There is also one shared bread pool and one shared  scrambled egg pool.

The manager of the factory specifies the number of sandwiches to pack. There is a log file containing records for logging when a bread slice is made, a portion of scrambled egg is made and a sandwich is packed. When all the sandwiches are packed, the manager will append a summary to the log file.

# How to use
Download the files, then run the sample input:
java SandwichManager 10 4 4 3 3 3 3 5 4

SAMPLE OUTPUT (in log.txt)
sandwiches:10
bread capacity:4
egg capacity:4
bread makers:3
egg makers:3
sandwich packers:3
bread rate:3
egg rate:5
packing rate:4

B2 puts bread 0
B0 puts bread 0
B0 puts bread 1
B0 puts bread 2
B0 puts bread 3
B0 puts bread 4
B0 puts bread 5
E1 puts egg 0
E1 puts egg 1
E1 puts egg 2
E1 puts egg 3
E1 puts egg 4
B0 puts bread 6
E1 puts egg 5
B1 puts bread 0
E1 puts egg 6
B1 puts bread 1
S0 packs sandwich 0 with bread 0 from B2 and egg 0 from E1 and bread 2 from B0
S1 packs sandwich 0 with bread 0 from B0 and egg 2 from E1 and bread 3 from B0
S2 packs sandwich 0 with bread 1 from B0 and egg 1 from E1 and bread 4 from B0
E1 puts egg 7
B1 puts bread 2
S2 packs sandwich 1 with bread 6 from B0 and egg 4 from E1 and bread 1 from B1
B0 puts bread 7
E1 puts egg 8
S1 packs sandwich 1 with bread 0 from B1 and egg 5 from E1 and bread 2 from B1
E1 puts egg 9
B0 puts bread 8
B0 puts bread 9
B0 puts bread 10
S0 packs sandwich 1 with bread 5 from B0 and egg 3 from E1 and bread 7 from B0
B0 puts bread 11
S0 packs sandwich 2 with bread 8 from B0 and egg 6 from E1 and bread 10 from B0
B1 puts bread 3
E1 puts egg 10
B1 puts bread 4
E2 puts egg 0
B1 puts bread 5
E2 puts egg 1
B1 puts bread 6
B0 puts bread 12
E2 puts egg 2
B1 puts bread 7
S0 packs sandwich 3 with bread 3 from B1 and egg 9 from E1 and bread 4 from B1
B1 puts bread 8
S2 packs sandwich 2 with bread 11 from B0 and egg 7 from E1 and bread 6 from B1
S1 packs sandwich 2 with bread 9 from B0 and egg 8 from E1 and bread 5 from B1
B1 puts bread 9
E2 puts egg 3
B0 puts bread 13
E1 puts egg 11

summary:
B0 makes 14
B1 makes 10
B2 makes 1
E0 makes 0
E1 makes 12
E2 makes 4
S0 packs 4
S1 packs 3
S2 packs 3
