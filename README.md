unfuel v0.95
======

A simple fuel log app, developed for personal use.

The code isn't completely clean (for example, there are prints I used to debug the code) and still needs some attention. I'll check it soon.
The documentation is still poor.

It has one major and obvious flaw: trip consumption is calculated using the fuel added in the last refill. 
So, if the user refuels before spending all the fuel added in the last refill, the calculated trip consumption will be erroneous. 
Considering this, I removed it since it is not accurate. I maintained the code that performs the trip consumption operations because I still haven't decided whether I will fix this or simply remove the functionality.

But since I developed this for personal use, and I'm more interested in the overall consumption, I still haven't addressed this issue.

The app uses CSV files to store data.

Feel free to use this, if you think it's useful.
