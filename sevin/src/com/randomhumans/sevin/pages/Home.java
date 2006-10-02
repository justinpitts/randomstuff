package com.randomhumans.sevin.pages;

import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.InjectPage;
import org.apache.tapestry.html.BasePage;

public abstract class Home extends BasePage
{
  public abstract String getQuery();
  
  public IPage doSubmit()
  {
      SearchResults p = getSearchResults();
      p.setQuery(getQuery());
      return p;      
  }
  
  @InjectPage("SearchResults")
  public abstract SearchResults getSearchResults();   

}
