package com.sajits.sajer.camunda.engine;

import com.sajits.sajer.camunda.utilities.Util;
import com.sajits.sajer.core.engine.EngineInterface;
import com.sajits.sajer.core.engine.TPManagementInterface;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.Tenant;
import org.camunda.bpm.engine.identity.User;

import static org.camunda.bpm.engine.authorization.Authorization.ANY;


public class CamundaEngine implements EngineInterface {

    private TPManagement tpmanagement = new TPManagement();

    @Override
    public void initializeEngine() {
        System.out.println("------------afterPropertiesSet()----------------------");
        BpmPlatform.getDefaultProcessEngine().getProcessEngineConfiguration().setUserResourceWhitelistPattern(".+");

        if (BpmPlatform.getDefaultProcessEngine().getIdentityService().createTenantQuery().tenantId("sajer")
                .count() == 0) {
            Tenant sajerTenant = BpmPlatform.getDefaultProcessEngine().getIdentityService().newTenant("sajer");
            sajerTenant.setName("sajer");
            BpmPlatform.getDefaultProcessEngine().getIdentityService().saveTenant(sajerTenant);
        }

        if (BpmPlatform.getDefaultProcessEngine().getIdentityService().createTenantQuery().tenantId("public")
                .count() == 0) {
            Tenant sajerTenant = BpmPlatform.getDefaultProcessEngine().getIdentityService().newTenant("public");
            sajerTenant.setName("public");
            BpmPlatform.getDefaultProcessEngine().getIdentityService().saveTenant(sajerTenant);
        }

        Group camundaAdminGroup = BpmPlatform.getDefaultProcessEngine().getIdentityService().createGroupQuery()
                .groupId("camunda-admin").singleResult();
        if (camundaAdminGroup == null) {
            camundaAdminGroup = BpmPlatform.getDefaultProcessEngine().getIdentityService().newGroup("camunda-admin");
            camundaAdminGroup.setName("Sajer Admin");
            camundaAdminGroup.setType("SYSTEM");
            BpmPlatform.getDefaultProcessEngine().getIdentityService().saveGroup(camundaAdminGroup);

            Permissions[] perm = { Permissions.ALL };
            Util.grouptGrant(Resources.APPLICATION, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.AUTHORIZATION, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.BATCH, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.DASHBOARD, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.DECISION_DEFINITION, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.DECISION_REQUIREMENTS_DEFINITION, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.DEPLOYMENT, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.FILTER, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.GROUP, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.GROUP_MEMBERSHIP, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.HISTORIC_PROCESS_INSTANCE, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.HISTORIC_TASK, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.OPERATION_LOG_CATEGORY, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.PROCESS_DEFINITION, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.PROCESS_INSTANCE, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.REPORT, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.TASK, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.TENANT, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.TENANT_MEMBERSHIP, camundaAdminGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.USER, camundaAdminGroup.getId(), ANY, perm);
            // Util.grouptGrant(Resources.CASE_DEFINITION, camundaAdminGroup.getId(), ANY,
            // perm);
            // Util.grouptGrant(Resources.CASE_INSTANCE, camundaAdminGroup.getId(), ANY,
            // perm);
            // Util.grouptGrant(Resources.CASE_EXECUTION, camundaAdminGroup.getId(), ANY,
            // perm);

            BpmPlatform.getDefaultProcessEngine().getIdentityService().createTenantGroupMembership("sajer",
                    camundaAdminGroup.getId());
            BpmPlatform.getDefaultProcessEngine().getIdentityService().createTenantGroupMembership("public",
                    camundaAdminGroup.getId());
        }

        Group publicGroup = BpmPlatform.getDefaultProcessEngine().getIdentityService().createGroupQuery()
                .groupId("public").singleResult();
        if (publicGroup == null) {
            publicGroup = BpmPlatform.getDefaultProcessEngine().getIdentityService().newGroup("public");
            publicGroup.setName("public");
            publicGroup.setType("public");
            BpmPlatform.getDefaultProcessEngine().getIdentityService().saveGroup(publicGroup);
            Permissions[] perm = { Permissions.ALL };
            Util.grouptGrant(Resources.APPLICATION, publicGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.FILTER, publicGroup.getId(), ANY, perm);
            Util.grouptGrant(Resources.PROCESS_DEFINITION, publicGroup.getId(), ANY, perm); //-- Temporary
            Permissions[] perm2 = { Permissions.CREATE };
            Util.grouptGrant(Resources.TASK, publicGroup.getId(), ANY, perm2);
            BpmPlatform.getDefaultProcessEngine().getIdentityService().createTenantGroupMembership("public",
                    publicGroup.getId());
        }

        if (BpmPlatform.getDefaultProcessEngine().getIdentityService().createUserQuery().userId("sajer").count() == 0) {
            User sajerUser = BpmPlatform.getDefaultProcessEngine().getIdentityService().newUser("sajer");
            sajerUser.setPassword("sajer");
            sajerUser.setFirstName("sajer");
            sajerUser.setLastName("sajer");
            BpmPlatform.getDefaultProcessEngine().getIdentityService().saveUser(sajerUser);
            BpmPlatform.getDefaultProcessEngine().getIdentityService().createMembership("sajer",
                    camundaAdminGroup.getId());
        }

    }

    @Override
    public TPManagementInterface getTPManagement() {
        return tpmanagement;
    }
}
