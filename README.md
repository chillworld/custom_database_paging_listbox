# custom database paging listbox
Custom listbox for easy database paging

Donations are always welcome, but certainly not obligated.<br/>
I like to help people where I can and share code.

<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&amp;business=WE5GFT33ZPFJW&amp;item_name=open-source&amp;item_number=custom_database_paging_listbox&amp;currency_code=EUR&amp;bn=PP%2dDonationsBF%3abtn_donate_LG%2egif%3aNonHosted"><img src="https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif" alt="[paypal]" /></a> 

Project is created for at least ZK version 6.5.0.
Older can be possible but it's not tested.

Under root there is 1 implementation, not included in the project because it's specific for spring data.
It has special sort features what you can add in zul.
Example : <listheader sort="client(lower(name),asc(birthdate),inverse(id))"/>
This means:
First sort property is name with ignore wase.
Second sort property is always ASC on birthdate.
Third sort property is on id but always the inverse direction.  So if name is ASC, sort on id is DESC.

You can download/copy the code if you want 


