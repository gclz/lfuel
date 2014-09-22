unfuel v0.95
======

A simple fuel log app.

The code isn't completely clean and still needs some attention. I'll check it soon.

It has one major and obvious flaw: trip consumption is calculated using the fuel added in the last refill. So, if the user refuels before spending all the fuel added in the last refill, the calculated trip consumption will be erroneous.

But since I developed this for personal use, and I'm more interested in the overall consumption, I still haven't addressed this issue.

The app uses CSV files to store data.

Feel free to use this, if you think it's useful.
