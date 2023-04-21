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
