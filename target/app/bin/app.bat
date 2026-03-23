@echo off
set JLINK_VM_OPTIONS=
set DIR=%~dp0
"%DIR%\java" %JLINK_VM_OPTIONS% -m org.httle.steiner.worktimerecord/org.httle.steiner.worktimerecord.WorkTimeRecord %*
