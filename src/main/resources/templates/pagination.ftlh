<script>
    let maxNumber = ${maxPageSize!};
</script>


<#if (maxPageSize > 1) >

    <ul class="pagination justify-content-center ">

        <li class="page-item altColor"><a class="page-link" href="${servletPath +"?page=" + 1 + "&&" +  queryName!}">&laquo;</a>
        </li>

        <li class="page-item "></li>
        <#if (currentPage >=  2) >
        <li class="page-item" id="next">
            <a onclick="previousPage()" class="page-link" aria-hidden="true" id="previous">&lsaquo;</a>
            </#if>

            <#list currentPage-2..currentPage+2 as i>

            <#if (i > 0 && i <= maxPageSize) >
        <li class="page-item <#if i = currentPage> active </#if>">
            <a class="page-link"
               href="${servletPath +"?page=" + i + "&&" +  queryName!}">${i}</a>
        </li>
        </#if>

        </#list>

        <#if (currentPage <=  (maxPageSize-1)) >
        <li class="page-item" id="next">
            <a onclick="nextPage()" class="page-link">&rsaquo;</a>
            </#if>
        </li>
        <li class="page-item">
            <a class="page-link" href="${servletPath +"?page=" + maxPageSize + "&&" +  queryName!}">&raquo;</a>
        </li>

    </ul>


</#if>
