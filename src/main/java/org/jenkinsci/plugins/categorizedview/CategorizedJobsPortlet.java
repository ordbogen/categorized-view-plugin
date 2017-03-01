package org.jenkinsci.plugins.categorizedview;

import hudson.Extension;
import hudson.model.ItemGroup;
import hudson.model.TopLevelItem;
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

public class CategorizedJobsPortlet extends DashboardPortlet {
	
	private List<CategorizationCriteria> categorizationCriteria;
	private transient CategorizedItemsBuilder categorizedItemsBuilder;
	
	@DataBoundConstructor
	public CategorizedJobsPortlet(String name, List<CategorizationCriteria> categorizationCriteria) {
		super(name);
		this.categorizationCriteria = categorizationCriteria;
	}
	
	public List<TopLevelItem> getGroupedItems() {
		List<TopLevelItem> items = getDashboard().getItems();
		
		categorizedItemsBuilder = new CategorizedItemsBuilder(items, categorizationCriteria);
		
		return categorizedItemsBuilder.getRegroupedItems();
	}
	
	public ItemGroup getOwnerItemGroup() {
		return getDashboard().getOwnerItemGroup();
	}

    public Collection<CategorizationCriteria> getCategorizationCriteria() {
		return categorizationCriteria;
	}
    
    public String getGroupClassFor(TopLevelItem item) {
    	return categorizedItemsBuilder.getGroupClassFor(item);
    }

	@Extension
	public static class DescriptorImpl extends Descriptor<DashboardPortlet> {
		public String getDisplayName() {
			return "Categorized Jobs View";
		}

		public List<Descriptor<CategorizationCriteria>> getCategorizationCriteriaDescriptors() {
			return new ArrayList<Descriptor<CategorizationCriteria>>(CategorizationCriteria.all());
		}
		
	    public boolean isGroupTopLevelItem(TopLevelItem item) {
	    	return item instanceof GroupTopLevelItem;
	    }
	}
	
    public boolean isGroupTopLevelItem(TopLevelItem item) {
    	return item instanceof GroupTopLevelItem;
    }
}
