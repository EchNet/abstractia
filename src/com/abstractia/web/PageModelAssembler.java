//
// PageModelAssembler.java
//

package com.abstractia.web;

import java.io.*;
import java.util.*;
import com.abstractia.display.*;
import com.abstractia.model.Record;
import com.abstractia.mockdex.NavigationResult;

/**
 * Page Builder?  Content Assembler?  Neither.
 * Process query results into a page model. 
 */
public class PageModelAssembler
    extends PipelineBlock
{
    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context)
        {
            public Object run()
                throws Exception
            {
                NavigationResult navResult = getContext().gett(NavigationResult.class, "navigationResult");

                PageModel page = new PageModel();

                HeadModel head = new HeadModel();
                String title = (String) getContext().get("display.title");
                if (title == null) title = "Abstractia";
                head.setTitle(title);
                page.setHead(head);

                ContainerModel body = new ContainerModel();
                page.setBody(body);

                LoginModel login = new LoginModel();
                login.setState(navResult.getState());
                login.setLoggedIn(getContext().get("display.loggedIn") != null);
                body.getComponents().add(login);

                if (navResult.getState().getDimFilters().size() > 0)
                {
                    BreadcrumbsModel crumbs = new BreadcrumbsModel();
                    crumbs.setDimFilters(navResult.getState().getDimFilters());
                    crumbs.setState(navResult.getState());
                    body.getComponents().add(crumbs);
                }

                ContainerModel leftStuff = new ContainerModel();

                if (navResult.getDimRefinements().size() > 0)
                {
                    NavLinksModel navLinks = new NavLinksModel();
                    navLinks.setDimRefinements(navResult.getDimRefinements());
                    navLinks.setState(navResult.getState());
                    leftStuff.getComponents().add(navLinks);
                }

                if (getContext().get("display.promo") != null)
                {
                    ImageModel image = new ImageModel();
                    image.setSource("img/" + getContext().get("display.promo"));
                    leftStuff.getComponents().add(image);
                }

                if (leftStuff.getComponents().size() > 0)
                {
                    SlotModel left = new SlotModel();
                    left.setName("left");
                    left.setComponent(leftStuff);
                    body.getComponents().add(left);
                }

                SlotModel center = new SlotModel();
                center.setName("center");
                ContainerModel centerStuff = new ContainerModel();

                if (getContext().get("display.featured") != null &&
                    navResult.getRecords().size() > 0)
                {
                    Record record = navResult.getRecords().get(0);
                    navResult.getRecords().remove(0);
                    FeaturedModel featured = new FeaturedModel();
                    featured.setRecord(record);
                    centerStuff.getComponents().add(featured);
                }

                ResultsModel results = new ResultsModel();
                results.setState(navResult.getState());
                results.setRecords(navResult.getRecords());
                results.setAtRecordEnd(navResult.isAtRecordEnd());
                centerStuff.getComponents().add(results);
                center.setComponent(centerStuff);
                body.getComponents().add(center);

                getContext().put("pageModel", page);
                return page;
            }
        };
    }
}
