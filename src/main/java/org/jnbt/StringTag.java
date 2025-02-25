package org.jnbt;

//@formatter:off

/*
* JNBT License
* 
* Copyright (c) 2010 Graham Edgecombe
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
* 
*     * Redistributions of source code must retain the above copyright notice,
*       this list of conditions and the following disclaimer.
*       
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in the
*       documentation and/or other materials provided with the distribution.
*       
*     * Neither the name of the JNBT team nor the names of its
*       contributors may be used to endorse or promote products derived from
*       this software without specific prior written permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
* AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
* IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
* LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
* SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
* INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE. 
*/

//@formatter:on

/**
 * The <code>TAG_String</code> tag.
 * 
 * @author Graham Edgecombe
 * 
 */
public final class StringTag extends Tag {
	
	/**
	 * The value.
	 */
	private final String value;
	
	/**
	 * Creates the tag.
	 * 
	 * @param name
	 *            The name.
	 * @param value
	 *            The value.
	 */
	public StringTag(final String name, final String value) {
	
		super(name);
		this.value = value;
	}
	
	@Override
	public String getValue() {
	
		return value;
	}
	
	@Override
	public String toString() {
	
		final String name = getName();
		String append = "";
		if ((name != null) && !name.equals("")) {
			append = "(\"" + getName() + "\")";
		}
		return "TAG_String" + append + ": " + value;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
	
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
	
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof StringTag other)) { return false; }
        if (value == null) {
            return other.value == null;
		} else return value.equals(other.value);
    }
	
}
