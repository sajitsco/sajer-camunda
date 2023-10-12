package com.sajits.sajer.camunda.utilities;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permission;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resource;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;



import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.BpmPlatform;

public class Util {
    public static void userGrant(Resource resource, String userId, java.lang.String resourceId, Permissions[] perms) {
		AuthorizationService authorizationService = BpmPlatform.getDefaultProcessEngine().getAuthorizationService();
		Authorization groupGrant = authorizationService.createNewAuthorization(1);
		groupGrant.setResource(resource);
		groupGrant.setResourceId(resourceId);
		for (Permission permission : perms) {
		 groupGrant.addPermission(permission);
		}
		groupGrant.setUserId(userId);
		authorizationService.saveAuthorization(groupGrant);
	}

    public static void grouptGrant(Resource resource, String groupId, String resourceId, Permission[] perms) {
        AuthorizationService authorizationService = BpmPlatform.getDefaultProcessEngine().getAuthorizationService();
        Authorization groupGrant = authorizationService.createNewAuthorization(1);
        groupGrant.setResource(resource);
        groupGrant.setResourceId(resourceId);
        for (Permission permission : perms) {
            groupGrant.addPermission(permission);
        }
        groupGrant.setGroupId(groupId);
        authorizationService.saveAuthorization(groupGrant);
   }

    public static void setAuthentication(String id) {
        List<String> groups = new ArrayList<String>();
        List<String> tenants = new ArrayList<String>();

        List<Group> grps = BpmPlatform.getDefaultProcessEngine().getIdentityService().createGroupQuery().groupMember(id).list();
		for (Group gr : grps) {
			groups.add(gr.getId());
            List<Tenant> ts = BpmPlatform.getDefaultProcessEngine().getIdentityService().createTenantQuery().groupMember(gr.getId()).list();
            for (Tenant tenant : ts) {
                if( tenants.lastIndexOf(tenant.getId()) < 0){
                    tenants.add(tenant.getId());
                }
		    }
		}

		List<Tenant> tnts = BpmPlatform.getDefaultProcessEngine().getIdentityService().createTenantQuery().userMember(id).list();
		for (Tenant tenant : tnts) {
            if( tenants.lastIndexOf(tenant.getId()) < 0){
                tenants.add(tenant.getId());
            }
		}

		BpmPlatform.getDefaultProcessEngine().getIdentityService().setAuthentication(id,  groups, tenants);
   }


}
