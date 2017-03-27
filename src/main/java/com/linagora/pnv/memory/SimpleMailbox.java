/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package com.linagora.pnv.memory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.linagora.pnv.Mailbox;
import com.linagora.pnv.MailboxACL;
import com.linagora.pnv.MailboxId;
import com.linagora.pnv.MailboxPath;
import com.linagora.pnv.MailboxSession;
import com.linagora.pnv.MailboxUtil;

public class SimpleMailbox implements Mailbox {

    private MailboxId id = null;
    private String namespace;
    private String user;
    private String name;
    private final long uidValidity;
    private MailboxACL acl = SimpleMailboxACL.EMPTY;

    public SimpleMailbox(MailboxPath path, long uidValidity, MailboxId mailboxId) {
        this.id = mailboxId;
        this.namespace = path.getNamespace();
        this.user = path.getUser();
        this.name = path.getName();
        this.uidValidity = uidValidity;
    }

    public SimpleMailbox(MailboxPath path, long uidValidity) {
        this(path, uidValidity, null);
    }

    public SimpleMailbox(Mailbox mailbox) {
        this.id = mailbox.getMailboxId();
        this.namespace = mailbox.getNamespace();
        this.user = mailbox.getUser();
        this.name = mailbox.getName();
        this.uidValidity = mailbox.getUidValidity();
    }

    public MailboxId getMailboxId() {
        return id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUidValidity() {
        return uidValidity;
    }

    @Override
    public MailboxPath generateAssociatedPath() {
        return new MailboxPath(getNamespace(), getUser(), getName());
    }

    /**
     * @see Object#equals(Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof SimpleMailbox) {
            SimpleMailbox o = (SimpleMailbox)obj;
            return Objects.equal(id, o.getMailboxId());
        }
        return false;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(namespace, user, name);
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("namespace", namespace)
            .add("user", user)
            .add("name", name)
            .toString();
    }

    @Override
    public void setMailboxId(MailboxId id) {
        this.id = id;
    }

    @Override
    public MailboxACL getACL() {
        return acl;
    }

    @Override
    public void setACL(MailboxACL acl) {
        this.acl = acl;
    }

    @Override
    public boolean isChildOf(Mailbox potentialParent, MailboxSession mailboxSession) {
        return MailboxUtil.isMailboxChildOf(this, potentialParent, mailboxSession);
    }
}