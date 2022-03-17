<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 3/13/2022
  Time: 1:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade show" id="screenListModal" tabindex="-1" role="dialog"
     data-keyboard="false">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"><i class="icon-menu7 mr-2"></i> &nbsp;Screen List</h5>
                <button type="button" class="close" id="closeBtn" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <hr class="mt-0">
                <div class="form-group-feedback form-group-feedback-left flex-grow-1">
                    <input type="text" class="form-control" id="autoCompleteScreenList" placeholder="Search"
                           data-popup="tooltip" title="" data-original-title="Type screen name">
                    <div class="form-control-feedback">
                        <i class="icon-search4 opacity-50">
                        </i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

