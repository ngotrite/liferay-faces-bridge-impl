/**
 * Copyright (c) 2000-2017 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.bridge.context.internal;

import javax.portlet.PortalContext;
import javax.portlet.PortletRequest;
import javax.portlet.faces.Bridge;
import javax.portlet.filter.PortalContextWrapper;

import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Kyle Stiemann
 */
public abstract class PortalContextBridgeCompatImpl extends PortalContextWrapper {

	// Private Data Members
	private boolean ajaxRequest;

	public PortalContextBridgeCompatImpl(PortalContext wrapped) {
		super(wrapped);
	}

	public PortalContextBridgeCompatImpl(PortletRequest portletRequest) {

		super(portletRequest.getPortalContext());

		String facesAjaxParam = portletRequest.getParameter(Bridge.FACES_AJAX_PARAMETER);

		this.ajaxRequest = BooleanHelper.isTrueToken(facesAjaxParam);
	}

	protected String getAddToHeadSupport(String addToHeadPropertyName, PortalContext wrappedPortalContext) {

		if (ajaxRequest) {
			return null;
		}
		else if (PortalContext.MARKUP_HEAD_ELEMENT_SUPPORT.equals(addToHeadPropertyName)) {
			return wrappedPortalContext.getProperty(addToHeadPropertyName);
		}
		else {
			return "true";
		}
	}
}
